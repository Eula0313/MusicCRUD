package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Music;
import com.example.demo.form.MusicForm;
import com.example.demo.service.MusicService;

@Controller
public class MusicController {

    @Autowired
    MusicService service;

    @GetMapping("index")
    public String indexView() {
        return "index";
    }

    @PostMapping(value = "menu", params = "select")
    public String listView(Model model) {
        Iterable<Music> list = service.findAll();
        model.addAttribute("list", list);
        return "list";
    }

    @PostMapping(value = "menu", params = "insert")
    public String musicInputView(Model model) {
        model.addAttribute("musicForm", new MusicForm());
        return "Music-input";
    }

    @PostMapping(value = "menu", params = "update")
    public String musicUpdateSelectView(Model model) {
        Iterable<Music> list = service.findAll();
        model.addAttribute("list", list);
        return "Music-new";
    }

    @PostMapping(value = "menu", params = "delete")
    public String musicDeleteSelectView(Model model) {
        Iterable<Music> list = service.findAll();
        model.addAttribute("list", list);
        return "Music-delete";
    }

    @PostMapping("confirm")
    public String musicConfirmView(MusicForm musicForm, Model model) {
        model.addAttribute("musicForm", musicForm);
        return "Music-confirm";
    }

    @PostMapping("insert")
    public String musicInsertView(MusicForm musicForm) {
        Music music = new Music(null, musicForm.getSong_name(), musicForm.getSinger());
        service.saveMusic(music);
        return "Music-complete";
    }

    @GetMapping("update")
    public String musicUpdateView(@RequestParam("id") Integer id, Model model) {
        Music music = service.findById(id);
        if (music == null) {
            model.addAttribute("errorMessage", "指定されたIDのデータは存在しません。");
            return musicUpdateSelectView(model);
        }
        model.addAttribute("musicForm", new MusicForm(music.getSong_id(), music.getSong_name(), music.getSinger()));
        return "Music-update";
    }

    @PostMapping("update")
    public String musicUpdate(MusicForm musicForm, Model model) {
        Music music = service.findById(musicForm.getSong_id());
        if (music == null) {
            model.addAttribute("errorMessage", "指定されたIDのデータは存在しません。");
            return musicUpdateSelectView(model);
        }
        music.setSong_name(musicForm.getSong_name());
        music.setSinger(musicForm.getSinger());
        service.updateMusic(music);
        return "update-complete";
    }

    @GetMapping("delete")
    public String musicDeleteView(@RequestParam("id") Integer id, Model model) {
        Music music = service.findById(id);
        if (music == null) {
            model.addAttribute("errorMessage", "指定されたIDのデータは存在しません。");
            return musicDeleteSelectView(model);
        }
        model.addAttribute("music", music);
        return "Music-delete";
    }

    @PostMapping("delete")
    public String musicDelete(@RequestParam("id") Integer id, Model model) {
        Music music = service.findById(id);
        if (music == null) {
            model.addAttribute("errorMessage", "指定されたIDのデータは存在しません。");
            return musicDeleteSelectView(model);
        }
        service.deleteMusic(id);
        return "delete-complete";
    }
}
