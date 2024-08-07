package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.dto.TagDto;
import dev.dkorez.msathesis.catalog.entity.TagDao;
import dev.dkorez.msathesis.catalog.mapper.TagMapper;
import dev.dkorez.msathesis.catalog.model.TagRequestDto;
import dev.dkorez.msathesis.catalog.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TagService {
    @Inject
    private TagRepository tagRepository;

    public List<TagDto> getTags() {
        return tagRepository.listAll().stream()
                .map(TagMapper::toDto)
                .toList();
    }

    public TagDto getTag(Long id) {
        TagDao entity = tagRepository.findById(id);
        return TagMapper.toDto(entity);
    }

    @Transactional
    public void create(TagRequestDto tag) {
        TagDao entity = new TagDao();
        entity.setName(tag.getName());

        tagRepository.persist(entity);
    }

    @Transactional
    public TagDto update(Long id, TagRequestDto tag) {
        TagDao entity = tagRepository.findById(id);
        entity.setName(tag.getName());

        tagRepository.persist(entity);
        return TagMapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
