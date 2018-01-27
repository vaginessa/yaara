package com.mlieou.yaara.widget;

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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.model.AbstractDrawerItem;
import com.mlieou.yaara.R;

import java.util.List;

public class ServerDrawerItem extends AbstractDrawerItem<ServerDrawerItem, ServerDrawerItem.ViewHolder> {

    String name;
    public ServerDrawerItem(String servername) {
        name = servername;
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.drawer_item_server;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.drawer_item_server;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.serverName.setText(name);
        holder.letterIcon.setLetter(name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LetterIcon letterIcon;
        TextView serverName;

        public ViewHolder(View itemView) {
            super(itemView);
            letterIcon = itemView.findViewById(R.id.li_icon);
            serverName = itemView.findViewById(R.id.tv_server_name);
        }
    }
}