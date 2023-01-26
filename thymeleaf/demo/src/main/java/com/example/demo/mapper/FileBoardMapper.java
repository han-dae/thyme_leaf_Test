package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bean.FileBoardVO;
import com.example.demo.bean.FileVO;

@Mapper
public interface FileBoardMapper {
    List<FileBoardVO> getFileBoardList(); 

    FileBoardVO fileBoardDetail(int b_no); 

    int fileBoardInsert(FileBoardVO fileBoard); 

    int fileBoardUpdate(FileBoardVO fileBoard); 

    int fileBoardDelete(int bno); 

    int fileInsert(FileVO file);
<<<<<<< HEAD

    int fileUpdate(FileVO file);
=======
    FileVO fileDetail(int b_no);
    int fileDelete(int b_no);
>>>>>>> 0c60248b9e466aaeac0298f5458fa447c90d9c5f
}
