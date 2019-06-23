package myplant.ssukssuk.controller;

import lombok.RequiredArgsConstructor;
import myplant.ssukssuk.model.Diary;
import myplant.ssukssuk.repository.DiaryRepository;
import myplant.ssukssuk.service.RepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryRepository diaryRepository;
    private final RepositoryService repositoryService;

//    일지들 불러오기
    @GetMapping("/diary")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("diary");
        List<Diary> diaryList = diaryRepository.findAll();
        modelAndView.addObject("diaryList", diaryList);
        return modelAndView;
    }

//    일지 보기
    @GetMapping("/content/{diary_id}")
    public ModelAndView content(@PathVariable Integer diary_id) {
        ModelAndView modelAndView = new ModelAndView("content");
        Diary diary = diaryRepository.findById(diary_id).get();
        modelAndView.addObject("wdate", diary.getWdate());
        modelAndView.addObject("content", diary.getContent());
        return modelAndView;
    }

//    일지 쓰기
    @GetMapping("/write")
    public ModelAndView write() {
        ModelAndView modelAndView = new ModelAndView("write");
        return modelAndView;
    }

//    일지 날리기
    @PostMapping("/insertDiary")
    public String insertDiary(@ModelAttribute Diary diary, Model model) {
        Diary entity = repositoryService.addDiary(diary);
        model.addAttribute(entity);
        return "redirect:/diary";
    }
}
