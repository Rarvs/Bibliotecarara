package com.example.bibliotecarara.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bibliotecarara.exceptions.CouldNotCreateEntityException;
import com.example.bibliotecarara.exceptions.CouldNotDeleteEntityException;
import com.example.bibliotecarara.exceptions.CouldNotUpdateEntityException;
import com.example.bibliotecarara.exceptions.NoEntityFoundException;
import com.example.bibliotecarara.model.WithCopy;

public abstract class BaseService<E extends WithCopy<E>> {
    JpaRepository<E, Long> repository;

    public BaseService(JpaRepository<E, Long> repository){
        this.repository = repository;
    }

    public E createEntity(E entity) throws CouldNotCreateEntityException{
        try{
            return repository.save(entity);
        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public List<E> saveAll(List<E> entities) throws CouldNotCreateEntityException{
        try {
            return repository.saveAll(entities);
        } catch (Exception e){
            throw new CouldNotCreateEntityException();
        }
    }

    public List<E> findAll(){
        try {
            return repository.findAll();
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public E getEntityById(long id) throws NoEntityFoundException{
        try{
            Optional<E> optEntity =  repository.findById(id);
            if(optEntity.isEmpty()){
                throw new NoEntityFoundException();
            }
            return optEntity.get();
        } catch (Exception e){
            throw new NoEntityFoundException();
        }
    }

    public E updateEntity(long id, E entity) throws NoEntityFoundException, CouldNotUpdateEntityException {
        try{
            Optional<E> optEntity = repository.findById(id);
            if(optEntity.isEmpty()){
                throw new NoEntityFoundException();
            }
            E updatedEntity = optEntity.get();
            updatedEntity.updateWith(entity);
            repository.save(updatedEntity);
            return updatedEntity;
        } catch (Exception e){
            throw new CouldNotUpdateEntityException();
        }
    }

    public E deleteEntity(long id) throws NoEntityFoundException, CouldNotDeleteEntityException {
        try{
            Optional<E> optEntity = repository.findById(id);
            if(optEntity.isEmpty()){
                throw new NoEntityFoundException();
            }
            repository.delete(optEntity.get());
            return optEntity.get();
        } catch (Exception e){
            throw new CouldNotDeleteEntityException();
        }
    }
}