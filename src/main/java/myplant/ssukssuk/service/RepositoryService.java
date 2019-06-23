package myplant.ssukssuk.service;

import myplant.ssukssuk.model.Diary;
import myplant.ssukssuk.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {
    @Autowired
    private DiaryRepository diaryRepository;

    public Diary addDiary(Diary diary) {
        return diaryRepository.save(diary);
    }
}
