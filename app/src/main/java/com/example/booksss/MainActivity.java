package com.example.booksss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Search search = new Search();
        Scanner sc = new Scanner(System.in);

        String key = sc.nextLine();
        ArrayList<Book> list = new ArrayList<>();
        search.initSearch(key);

        char flag;
        while(true){
            list = search.Search();
            if(list.size() == 0) break;
            for(int i=0; i<10 && i<list.size(); i++)
                System.out.println(list.get(i).title);
            System.out.println("n(N) : next, the other : exit");
            flag = sc.next().charAt(0);
            if(!(flag == 'n' || flag == 'N')) break;

        }
}