package com.coursepoject.service;

import com.coursepoject.data.Topic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    private List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("java","Core Java","Core Java Description"),
            new Topic("javascript","Java Script","Java Script Description"),
            new Topic("spring","Spring Framework","Spring Framework Description")
    ));

/*
    @Autowired // injected by Spring when creating instance of this class( TopicService)
    private TopicRepository topicRepository;
*/

    public List<Topic> getAllTopics() {
       // List<Topic> topics = new ArrayList<>();
        //topicRepository.findAll().forEach(topics::add);
        return topics;
    }

    public Topic getTopic(String id)
    {
        return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public List<Topic> addTopic(Topic topic) {
        topics.add(topic);
       // topicRepository.save(topic);
        return getAllTopics();
    }

    public List<Topic> deleteTopic(String id) {
        topics.removeIf(topic1 -> topic1.getId().equals(id));
        //or below also works
        // topics.remove(topics.stream().filter(topic1 -> topic1.getId().equals(id)).findFirst().get());
       // topicRepository.delete(id);
        return getAllTopics();
    }

    public List<Topic> updateTopic(Topic topic,String id) {
        topics.remove(topics.stream().filter(topic1 -> topic1.getId().equals(id)).findFirst().get());
        //topics.set(topics.stream().filter(topic1 -> topic1.getId().equals(id)));

        topics.add(topic);
        //topicRepository.
        return getAllTopics();
    }
}
