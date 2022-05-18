import profile from 'app/entities/profile/profile.reducer';
import searchHistory from 'app/entities/search-history/search-history.reducer';
import age from 'app/entities/age/age.reducer';
import searchSetting from 'app/entities/search-setting/search-setting.reducer';
import readingList from 'app/entities/reading-list/reading-list.reducer';
import note from 'app/entities/note/note.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  profile,
  searchHistory,
  age,
  searchSetting,
  readingList,
  note,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
