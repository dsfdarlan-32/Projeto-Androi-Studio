package com.example.darlan.cadastraaluno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private static  final  int VERSAO_BANCO = 1 ;
    private  static final  String BANCO_ALUNO = "bd_aluno";
    private  static final  String TABELA_ALUNO = "tb_aluno";
    private  static final  String COLUNA_CODIGO = "codigo";
    private  static final  String COLUNA_NOME = "nome";
    private  static final  String COLUNA_EMAIL = "email";

    public BancoDados(Context context) {
        super(context, BANCO_ALUNO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_ALUNO + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY , "
                + COLUNA_NOME + " TEXT, "
                + COLUNA_EMAIL + " TEXT)";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    /* CRUD */

    void addAluno (Aluno aluno){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put( COLUNA_NOME, aluno.getNome());
        values.put( COLUNA_EMAIL, aluno.getEmail());

        db.insert(TABELA_ALUNO, null,values);
        db.close();
    }

    void apagarAluno (Aluno aluno){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_ALUNO,COLUNA_CODIGO + " = ?", new String[] { String.valueOf(aluno.getCodigo())});
        db.close();
    }

    Aluno selecinarAluno(int codigo){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_ALUNO, new String[] {COLUNA_CODIGO,
        COLUNA_NOME, COLUNA_EMAIL}, COLUNA_CODIGO + " = ?", new String[] { String.valueOf(codigo)}, null ,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Aluno aluno = new Aluno(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

    return  aluno;
    }

    void atualizarAluno(Aluno aluno) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put( COLUNA_NOME, aluno.getNome());
        values.put( COLUNA_EMAIL, aluno.getEmail());

        db.update(TABELA_ALUNO, values, COLUNA_CODIGO + " = ?",
               new String[] {String.valueOf(aluno.getCodigo()) } );

    }


 public List<Aluno> listaTodosAlunos (){
        List<Aluno> listaAlunos = new ArrayList<Aluno>();

        String query = "SELECT * FROM " + TABELA_ALUNO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query,null  );

        if (c.moveToFirst()){

            do{
                Aluno aluno = new Aluno();
                aluno.setCodigo(Integer.parseInt(c.getString(0)));
                aluno.setNome(c.getString(1));
                aluno.setEmail(c.getString(2));

                listaAlunos.add(aluno  );


            } while (c.moveToNext());



        }

        return listaAlunos;
    }
}
