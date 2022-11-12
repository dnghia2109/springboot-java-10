package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.dto.PublisherRequest;
import vn.techmaster.bookonline.entity.Publisher;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.PublisherRepository;

import java.util.List;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    // Find by id
    public Publisher findById(String id) {
        return publisherRepository.findById(id).orElseThrow(() -> new NotFoundException("No publisher with id = " + id));
    }

    // Find all
    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    // Save entity
    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    // Map request from entity
    public PublisherRequest mapRequestEntity(Publisher publisher) {
        PublisherRequest publisherRequest = new PublisherRequest();

        publisherRequest.setId(publisher.getId());
        publisherRequest.setName(publisher.getName());

        return publisherRequest;
    }

    // Save entity by request
    public Publisher saveByRequest(PublisherRequest publisherRequest) {
        Publisher publisher;
        if (publisherRequest.getId() == null) {
            publisher = new Publisher();
        } else {
            publisher = findById(publisherRequest.getId());
        }

        publisher.setName(publisherRequest.getName());

        return publisherRepository.save(publisher);
    }

    // Delete by id
    public void deleteById(String id) {
        publisherRepository.deleteById(id);
    }

    // Find all, pageable order by name
    public Page<Publisher> findByOrderByNameAsc(Pageable pageable) {
        return publisherRepository.findByOrderByNameAsc(pageable);
    }

    // Check exists by name
    public boolean existsByName(String name) {
        return publisherRepository.existsByName(name);
    }
}
