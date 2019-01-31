package com.zyh.wanandroid.adapter;

import android.support.design.internal.FlowLayout;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.bean.Knowledge;

import java.util.List;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class KnowledgeAdapter extends BaseQuickAdapter<Knowledge, BaseViewHolder> {
    private OnItemChildClickListener mOnItemChildClickListener;

    public KnowledgeAdapter(List<Knowledge> mList) {
        super(R.layout.item_knowledge, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Knowledge item) {
        helper.setText(R.id.tv_name, item.getName());
        FlowLayout layoutFlow = helper.getView(R.id.flow_layout);
        layoutFlow.removeAllViews();
        List<Knowledge.ChildrenBean> children = item.getChildren();
        if (children == null || children.isEmpty()) {
            layoutFlow.setVisibility(View.GONE);
            return;
        }
        layoutFlow.setVisibility(View.VISIBLE);
        for (int i = 0; i < children.size(); i++) {
            Knowledge.ChildrenBean tree = children.get(i);
            View child = View.inflate(mContext, R.layout.layout_tag_knowledge_tree, null);
            TextView textView = child.findViewById(R.id.tv_tag);

            String name = tree.getName();
            SpannableString content = new SpannableString(name);
            content.setSpan(new UnderlineSpan(), 0, name.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(content);

            child.setOnClickListener(v -> mOnItemChildClickListener.onItemChildClick(this, v, tree.getId()));

            layoutFlow.addView(child);
        }
    }


    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }
}
