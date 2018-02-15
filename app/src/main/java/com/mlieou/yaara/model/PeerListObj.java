package com.mlieou.yaara.model;

import java.util.List;

/**
 * Created by mlieou on 2/12/18.
 */

public class PeerListObj {
    List<Peer> peerList;

    public PeerListObj(List<Peer> peerList) {
        this.peerList = peerList;
    }

    public List<Peer> getPeerList() {
        return peerList;
    }
}
