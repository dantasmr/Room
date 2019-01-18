package br.com.alura.roomapplication.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AlunoDao;
import br.com.alura.roomapplication.database.AluraDatabase;
import br.com.alura.roomapplication.database.GeradorBancoDados;
import br.com.alura.roomapplication.delegate.AlunosDelegate;
import br.com.alura.roomapplication.models.Aluno;

public class ListaAlunosFragment extends Fragment {

    private AlunosDelegate delegate;
    private ListView lista;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        delegate = (AlunosDelegate) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lista_alunos_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final Context context = getContext();

        final EditText busca = new EditText(context);
        busca.setInputType(InputType.TYPE_CLASS_TEXT);
        busca.setHint("Digite o nome");

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(busca);

        if (R.id.lista_menu_alunos_busca == item.getItemId()) {
            AlertDialog show = new AlertDialog.Builder(getContext())
                    .setView(linearLayout)
                    .setMessage("Informe a busca")
                    .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String strbusca = busca.getText().toString();
                            GeradorBancoDados gerador = new GeradorBancoDados();
                            AluraDatabase alura = gerador.gera(context);
                            AlunoDao alunoDao = alura.getAlunoDao();
                            List<Aluno> alunos = alunoDao.getAlunos(strbusca);
                            configuraAdapter(getContext(), alunos);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        GeradorBancoDados gerador = new GeradorBancoDados();
        AluraDatabase alura = gerador.gera(getContext());
        final AlunoDao alunoDao = alura.getAlunoDao();

        lista = view.findViewById(R.id.fragment_lista);
        List<Aluno> alunos = alunoDao.busca();

        final ArrayAdapter<Aluno> adapter = configuraAdapter(getContext(), alunos);

        final FloatingActionButton botaoAdd = view.findViewById(R.id.fragment_lista_fab);
        botaoAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                delegate.addAluno();
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(i);
                delegate.selecionaAluno(aluno);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Aluno aluno = (Aluno) lista.getItemAtPosition(i);
                String mensagem = "Excluir aluno " + aluno.getNome() + " ?";
                Snackbar.make(botaoAdd, mensagem, Snackbar.LENGTH_SHORT)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alunoDao.deleta(aluno);
                                adapter.remove(aluno);
                            }
                        }).show();
                return true;
            }
        });

        return view;

    }

    private ArrayAdapter<Aluno> configuraAdapter(Context context, List<Aluno> alunos) {
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, alunos);
        lista.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraTitulo("Lista de Alunos");
    }
}
