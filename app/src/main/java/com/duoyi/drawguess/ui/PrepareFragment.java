package com.duoyi.drawguess.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.duoyi.drawguess.AppContext;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.api.AppSocket;
import com.duoyi.drawguess.base.RvBaseAdapter;
import com.duoyi.drawguess.base.ViewHolder;
import com.duoyi.drawguess.model.Player;
import java.util.ArrayList;

/**
 * 准备界面
 */
public class PrepareFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView seatRv;
    private ArrayList<Player> sittingPlayers;
    private RvBaseAdapter<Player> seatAdapter;
    private Button readyBtn;

    public PrepareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param players 玩家
     * @return A new instance of fragment PrepareFragment.
     */
    public static PrepareFragment newInstance(ArrayList<Player> players) {
        PrepareFragment fragment = new PrepareFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("players", players);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        sittingPlayers = bundle.getParcelableArrayList("players");
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_ready, container, false);
        readyBtn = root.findViewById(R.id.btn_ready);
        readyBtn.setOnClickListener(this);
        seatRv = root.findViewById(R.id.rv_dg_seat);
        root.findViewById(R.id.tv_invite).setOnClickListener(this);
        root.findViewById(R.id.tv_exit).setOnClickListener(this);
        return root;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRv();
    }

    private void initRv() {
        if (sittingPlayers == null) {
            sittingPlayers = new ArrayList<>();
        }
        seatRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        seatAdapter = new RvBaseAdapter<Player>(sittingPlayers, R.layout.item_room_prepare_seat) {
            @Override public void onBind(ViewHolder holder, Player model) {
                holder.setText(R.id.tv_user_name, model.getName())
                        .setVisible(R.id.iv_user_status, model.isReady())
                        .showNetImage(R.id.iv_user_avatar, model.getAvatar());
            }
        };
        seatRv.setAdapter(seatAdapter);
    }

    public void addPlayer(Player player) {
        sittingPlayers.add(player);
        seatAdapter.notifyItemInserted(sittingPlayers.size());
    }

    public void setPlayerReady(int index) {
        if (index >= sittingPlayers.size()) {
            return;
        }
        seatAdapter.notifyItemChanged(index);
    }

    public void removePlayer(int index) {
        if (index >= sittingPlayers.size()) {
            return;
        }
        sittingPlayers.remove(index);
        seatAdapter.notifyItemRemoved(index);
        seatAdapter.notifyItemRangeChanged(index, seatAdapter.getItemCount() - 1);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ready:
                AppSocket.get().readyDG();
                int i = 0;
                for (Player player : sittingPlayers) {
                    if (player.getId().equals(AppContext.getInstance().getUser().getId())) {
                        player.setReady(true);
                        seatAdapter.notifyItemChanged(i);
                        break;
                    }
                    i++;
                }
                readyBtn.setText("已准备");
                readyBtn.setEnabled(false);
                break;
            case R.id.tv_invite:
                break;
            case R.id.tv_exit:
                getActivity().finish();
                break;
        }
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
