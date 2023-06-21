package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    private static Long idCounter = 1L;

    @Override
    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty remove(Long id) {
        Faculty Faculty = get(id);
        repository.deleteById(id);
        return Faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        Faculty existedFaculty = get(faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public Collection<Faculty> getAll() {
        return repository.findAll();
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = repository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new EntityNotFoundException();
        }
    }


    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        if (!StringUtils.hasText(name) && !StringUtils.hasText(color)) {
            throw new IncorrectArgumentException("Неккоректное наименование или цвет факультета");
        }
        return repository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
