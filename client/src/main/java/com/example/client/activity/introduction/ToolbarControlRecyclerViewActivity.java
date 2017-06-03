/*
 * Copyright 2014 Soichiro Kashima
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

package com.example.client.activity.introduction;

import android.support.v7.widget.LinearLayoutManager;

import com.example.client.R;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

public class ToolbarControlRecyclerViewActivity extends ToolbarControlBaseActivity<ObservableRecyclerView> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_toolbarcontrolrecyclerview;
    }

    @Override
    protected ObservableRecyclerView createScrollable() {
        ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.scrollable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        setDummyData(recyclerView);
        return recyclerView;
    }
}