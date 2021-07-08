package info.andchelp.fitwf.mapper;

import java.util.Collection;

public interface CollectionMapper<A, B> {

    Collection<A> map(Collection<B> dto);

}
