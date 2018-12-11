package assignment.coding.maersk.weatherapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import assignment.coding.maersk.weatherapp.weatherapp.dao.SearchItemDAO;
import assignment.coding.maersk.weatherapp.weatherapp.database.SearchItemDatabase;
import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;
import static org.junit.Assert.assertEquals;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SearchItemDatabaseTest {

    private SearchItemDAO mDAO;
    private SearchItemDatabase mDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDatabase = Room.inMemoryDatabaseBuilder(context, SearchItemDatabase.class).build();
        mDAO = mDatabase.SearchItemDAO();
    }

    @After
    public void closeDb() throws IOException {
        mDatabase.close();
    }

    @Test
    public void writeSearchitemNread() throws Exception {
        mDAO.insert( new SearchItem( "Hyderabad" ) );
        mDAO.insert( new SearchItem( "Hariyana" ) );
        mDAO.insert( new SearchItem( "Delhi" ) );
        mDAO.insert( new SearchItem( "Dehradoon" ) );
        List<SearchItem> result =  mDAO.getSearchItems( "H" ).getValue();
        List<SearchItem> expected = new ArrayList<>();
        expected.add( new SearchItem( "Hyderabad" )  );
        expected.add( new SearchItem( "Hariyana" )  );
        assertEquals(result.size(),expected.size()  );

        for (int i=0; i<result.size(); i++) {
            assertEquals( result.get(i).getsearchItem(),expected.get(i).getsearchItem() );
        }

    }
}
