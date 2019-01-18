package br.com.alura.roomapplication.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.delegate.AlunosDelegate;
import br.com.alura.roomapplication.fragments.FormularioAlunosFragment;
import br.com.alura.roomapplication.fragments.ListaAlunosFragment;
import br.com.alura.roomapplication.models.Aluno;

public class AlunosActivity extends AppCompatActivity implements AlunosDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        exibeFrament(new ListaAlunosFragment(), false);
    }


    public void exibeFrament(Fragment fragment, boolean empilhar) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.alunos_frame, fragment);

        if (empilhar){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    @Override
    public void addAluno() {
        exibeFrament(new FormularioAlunosFragment(), true);
    }

    @Override
    public void alteraTitulo(String titulo) {
        setTitle(titulo);
    }

    @Override
    public void voltar() {
        onBackPressed();
    }

    @Override
    public void selecionaAluno(Aluno aluno) {
        FormularioAlunosFragment formulario = new FormularioAlunosFragment();
        Bundle argumentos = new Bundle();
        argumentos.putSerializable("aluno", aluno);
        formulario.setArguments(argumentos);
        exibeFrament(formulario, true);
    }
}
