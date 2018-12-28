// IBookManager.aidl
package com.jjy.aidl.serve;

// Declare any non-default types here with import statements
import com.jjy.aidl.serve.Book;
interface IBookManager {
    /**
        * 获取图书列表
        */
       List<Book> getBookList();

       /**
        * 添加图书
        */
       void addBook(in Book book);
}
