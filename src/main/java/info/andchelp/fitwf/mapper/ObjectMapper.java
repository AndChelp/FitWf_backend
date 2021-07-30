package info.andchelp.fitwf.mapper;

public interface ObjectMapper<A, B> {

    A map(B dto);

}
