package info.andchelp.fitwf.mapper;

import java.util.Collection;

public interface ToDtoMapper<E, D> {

    D toDto(E entity);

    Collection<D> toDto(Collection<E> entities);

}
