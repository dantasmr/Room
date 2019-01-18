package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AlunoDao;
import br.com.alura.roomapplication.database.AluraDatabase;
import br.com.alura.roomapplication.database.GeradorBancoDados;
import br.com.alura.roomapplication.delegate.AlunosDelegate;
import br.com.alura.roomapplication.models.Aluno;

public class FormularioAlunosFragment extends Fragment {


    private Aluno aluno = new Aluno();
    private EditText campoNome;
    private EditText campoEmail;
    AlunosDelegate delegate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.delegate = (AlunosDelegate)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_alunos, container, false);


        this.campoNome = view.findViewById(R.id.formulario_alunos_nome);
        this.campoEmail = view.findViewById(R.id.formulario_alunos_email);

        final Button cadastrar = view.findViewById(R.id.formulario_alunos_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesAluno();
                Toast.makeText(getContext(), aluno.getNome(),Toast.LENGTH_SHORT).show();
                delegate.voltar();
            }
        });

        Bundle argumentos = getArguments();
        if (argumentos != null){
            this.aluno = (Aluno) argumentos.getSerializable("aluno");
            campoNome.setText(aluno.getNome());
            campoEmail.setText(aluno.getEmail());
        }


        return view;
    }

    private void atualizaInformacoesAluno(){
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        GeradorBancoDados geradorBancoDador = new GeradorBancoDados();
        AluraDatabase alura = geradorBancoDador.gera(getContext());
        AlunoDao alunoDao = alura.getAlunoDao();
        if (aluno.getId() == 0l) {
            alunoDao.insere(aluno);
        }else{
            alunoDao.altera(aluno);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraTitulo("Formulario de Alunos");
    }
}
