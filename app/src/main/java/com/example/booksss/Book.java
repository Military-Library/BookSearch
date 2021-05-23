package com.example.booksss;
public class Book {
    String image;
    String author;
    String price;
    String discount;
    String link;
    String publisher;
    String description;
    String title;

    /* init */
    Book(String image, String author, String price, String discount, String link, String publisher,
         String description, String title){
        this.image = image;
        this.author = author;
        this.price = price;
        this.discount = discount;
        this.link = link;
        this.publisher = publisher;
        this.description = description;
        this.title = title;
    }



    /* 멤버변수 호출 함수 */
    String getImage(){
        return this.image;
    }

    String getAuthor(){
        return this.author;
    }

    String getPrice(){
        return this.price;
    }

    String getDiscount(){
        return this.discount;
    }

    String getLink(){
        return this.link;
    }

    String getPublisher(){
        return this.publisher;
    }

    String getDescription(){
        return this.description;
    }

    String getTitle(){
        return this.title;
    }
    /* end. */
}