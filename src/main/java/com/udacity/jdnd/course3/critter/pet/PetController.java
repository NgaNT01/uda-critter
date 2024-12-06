package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet entity = modelMapper.map(petDTO, Pet.class);

        Pet pet = petService.create(entity);
        return modelMapper.map(pet, PetDTO.class);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findByPetId(petId);

        return modelMapper.map(pet, PetDTO.class);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petList = new ArrayList<>();
        for (Pet entity : petService.list()) {
            petList.add(modelMapper.map(entity, PetDTO.class));
        }

        return petList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petEntityList = petService.findByOwnerId(ownerId);
        if (petEntityList.isEmpty())
            return null;

        List<PetDTO> petList = new ArrayList<>();
        for (Pet entity : petEntityList) {
            petList.add(modelMapper.map(entity, PetDTO.class));
        }

        return petList;
    }
}
