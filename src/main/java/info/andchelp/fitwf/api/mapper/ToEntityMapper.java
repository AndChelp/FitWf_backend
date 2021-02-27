package info.andchelp.fitwf.api.mapper;

import java.util.Collection;

public interface ToEntityMapper<E, D> {

    E toEntity(D dto);

    Collection<E> toEntity(Collection<D> dto);


}
