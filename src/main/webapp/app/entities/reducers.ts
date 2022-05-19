import profile from 'app/entities/profile/profile.reducer';
import searchHistory from 'app/entities/search-history/search-history.reducer';
import searchSetting from 'app/entities/search-setting/search-setting.reducer';
import readingList from 'app/entities/reading-list/reading-list.reducer';
import note from 'app/entities/note/note.reducer';
import ageCodeSet from 'app/entities/age-code-set/age-code-set.reducer';
import educationLevelCodeSet from 'app/entities/education-level-code-set/education-level-code-set.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  profile,
  searchHistory,
  searchSetting,
  readingList,
  note,
  ageCodeSet,
  educationLevelCodeSet,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
