package com.example.demo.service;

import com.example.demo.entity.Music;

public interface MusicService {
    Iterable<Music> findAll();
    Music findById(Integer id);
    void saveMusic(Music music);
    void updateMusic(Music music);
    void deleteMusic(Integer id);
}
