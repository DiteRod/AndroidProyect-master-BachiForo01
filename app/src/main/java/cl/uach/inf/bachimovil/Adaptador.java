package cl.uach.inf.bachimovil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> implements View.OnClickListener {

    ArrayList<Post> listaPost;
    private View.OnClickListener listener;
    public Adaptador(ArrayList<Post> listaPost) {
        this.listaPost = listaPost;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, null, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.Titulo.setText(listaPost.get(position).getTitulo());
        holder.Taqs.setText(listaPost.get(position).getTaqs());
    }

    @Override

    public int getItemCount() {
        return listaPost.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Titulo,Taqs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = (TextView) itemView.findViewById(R.id.idNombre);
            Taqs = (TextView) itemView.findViewById(R.id.idInfo);
            ;

        }
    }
}