/*
 *         YAARA - Aria2 Remote for Android
 *
 * Copyright 2017-2018 Mlieou <ifddd@outlook.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mlieou.yaara.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.data.YaaraDataStore;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ServerAdapterViewHolder> {

    public static final String[] PROJECTION = {
            YaaraDataStore.Servers._ID,
            YaaraDataStore.Servers.NAME,
            YaaraDataStore.Servers.HOSTNAME,
            YaaraDataStore.Servers.PORT,
            YaaraDataStore.Servers.SECRET_TOKEN
    };

    public static final int ID_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int HOSTNAME_INDEX = 2;
    public static final int PORT_INDEX = 3;
    public static final int SECRET_TOKEN_INDEX = 4;

    private Cursor mCursor;
    private Context mContext;

    public ServerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ServerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drawer_item_server, parent, false);
        return new ServerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServerAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String name = mCursor.getString(NAME_INDEX);
        String hostname = mCursor.getString(HOSTNAME_INDEX);
        int port = mCursor.getInt(PORT_INDEX);
        String secretToken = mCursor.getString(SECRET_TOKEN_INDEX);

        holder.serverName.setText(name);
    }

    public void swapData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    class ServerAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView serverName;
        private

        ServerAdapterViewHolder(View itemView) {
            super(itemView);
            serverName = itemView.findViewById(R.id.tv_server_name);
        }
    }
}
