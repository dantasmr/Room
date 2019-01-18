package br.com.alura.roomapplication.delegate;


import android.support.v4.app.Fragment;

import br.com.alura.roomapplication.models.Aluno;

public interface AlunosDelegate {
    public void addAluno();
    public void alteraTitulo(String titulo);
    public void voltar();
    public void selecionaAluno(Aluno aluno);

}
