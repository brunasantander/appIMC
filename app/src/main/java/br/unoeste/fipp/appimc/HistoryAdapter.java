package br.unoeste.fipp.appimc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<UserData> userDataList;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return userDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.history_item, parent, false);
            holder = new ViewHolder();
            holder.itemName = convertView.findViewById(R.id.itemNome);
            holder.itemWeight = convertView.findViewById(R.id.itemPeso);
            holder.itemHeight = convertView.findViewById(R.id.itemAltura);
            holder.itemBmi = convertView.findViewById(R.id.itemIMC);
            holder.itemCondition = convertView.findViewById(R.id.itemCondicao);
            holder.itemDate = convertView.findViewById(R.id.itemData);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserData currentItem = userDataList.get(position);
        holder.itemName.setText("Nome: " + currentItem.getNome());
        holder.itemWeight.setText("Peso: " + currentItem.getPeso() + " kg");
        holder.itemHeight.setText("Altura: " + currentItem.getAltura() + " m");
        holder.itemBmi.setText("IMC: " + String.format("%.2f", currentItem.getImc()));
        holder.itemCondition.setText("Condição: " + currentItem.getCondicaoFisica());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        holder.itemDate.setText("Data: " + dateFormat.format(currentItem.getDataCalculo()));

        return convertView;
    }

    static class ViewHolder {
        TextView itemName;
        TextView itemWeight;
        TextView itemHeight;
        TextView itemBmi;
        TextView itemCondition;
        TextView itemDate;
    }
}