package challenge.leandrini;

import jakarta.inject.Named;

import java.util.Date;

@Named
public class GetDate implements IGetDate{

    @Override
    public Date now() {
        return new Date();
    }

}
