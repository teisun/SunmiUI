package sunmi.sunmidesign;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RefreshAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mSourceData;

	public RefreshAdapter(Context mContext, List<String> mSourceData) {
		super();
		this.mContext = mContext;
		this.mSourceData = mSourceData;
	}

	@Override
	public int getCount() {
		return mSourceData.size();
	}

	@Override
	public Object getItem(int position) {
		return mSourceData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_lv, null);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String title = mSourceData.get(position);
		holder.txtTitle.setText(title);

		return convertView;
	}

	static class ViewHolder {
		TextView txtTitle;
	}
}
