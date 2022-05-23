import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Profile from './profile';
import SearchHistory from './search-history';
import SearchSetting from './search-setting';
import ReadingList from './reading-list';
import Note from './note';
import AgeCodeSet from './age-code-set';
import EducationLevelCodeSet from './education-level-code-set';
import UserFavourites from "app/entities/reading-list/user-favourites";
import UserSearchSetting from "app/entities/search-setting/user-search-setting";
import UserSearchHistory from "app/entities/search-history/user-search-history";
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}profile`} component={Profile} />
        <ErrorBoundaryRoute path={`${match.url}search-history`} component={SearchHistory} />
        <ErrorBoundaryRoute path={`${match.url}search-setting`} component={SearchSetting} />
        <ErrorBoundaryRoute path={`${match.url}reading-list`} component={ReadingList} />
        <ErrorBoundaryRoute path={`${match.url}user-favourites`} component={UserFavourites} />
        <ErrorBoundaryRoute path={`${match.url}user-search-settings`} component={UserSearchSetting} />
        <ErrorBoundaryRoute path={`${match.url}user-search-history`} component={UserSearchHistory} />

        <ErrorBoundaryRoute path={`${match.url}note`} component={Note} />
        <ErrorBoundaryRoute path={`${match.url}age-code-set`} component={AgeCodeSet} />
        <ErrorBoundaryRoute path={`${match.url}education-level-code-set`} component={EducationLevelCodeSet} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
