package br.com.alura.roomapplication.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import static android.os.Build.VERSION_CODES.M;

public class GeradorBancoDados {

    public AluraDatabase gera(Context contexto) {
        AluraDatabase db = Room.databaseBuilder(contexto, AluraDatabase.class, "aluraDB")
                .allowMainThreadQueries()
                //.fallbackToDestructiveMigration() //permite deletar tudo e reiniciar
                .addMigrations(versaoNova())
                .build();
        return db;
    }

    private Migration versaoNova() {
        return new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                String sql = "alter table Aluno add column nascimento integer DEFAULT 1547601502";
                database.execSQL(sql);
            }
        };

    }

}
