package cum.shplendit.a2prako;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ZakazAdapter extends RecyclerView.Adapter<ZakazAdapter.ZakazViewHolder> {

    private List<ZakazItem> zakazList;

    public ZakazAdapter(List<ZakazItem> zakazList) {
        this.zakazList = zakazList;
    }

    @NonNull
    @Override
    public ZakazViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zakaz, parent, false);
        return new ZakazViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZakazViewHolder holder, int position) {
        ZakazItem zakaz = zakazList.get(position);
        holder.tvNomer.setText("№ " + zakaz.getNomerZakaza());
        holder.tvGitara.setText(zakaz.getGitaraModel());
    }

    @Override
    public int getItemCount() {
        return zakazList.size();
    }

    public class ZakazViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomer, tvGitara;

        public ZakazViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomer = itemView.findViewById(R.id.tvNomer);
            tvGitara = itemView.findViewById(R.id.tvGitara);
        }
    }
}