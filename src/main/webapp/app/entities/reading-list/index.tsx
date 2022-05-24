import React from 'react';
import {Switch} from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ReadingList from './reading-list';
import ReadingListUpdate from './reading-list-update';
import ReadingListDeleteDialog from './reading-list-delete-dialog';
import {useAppSelector} from "app/config/store";
import ReadingListDetail from "app/entities/reading-list/reading-list-detail";
import Favorites from "app/modules/favorites";

const Routes = ({ match }) => {

  const account = useAppSelector(state => state.authentication.account);

  return  (
    <>
      <Switch>
        <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReadingListUpdate} />
        <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReadingListUpdate} />
        {/* <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={account && account.authorities.includes('ROLE_ADMIN') ? ReadingListDetail: FavoritesCard} />  */}
        <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReadingListDetail} />
        <ErrorBoundaryRoute path={match.url} component={account && account.authorities.includes('ROLE_ADMIN') ? ReadingList : Favorites} />
      </Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReadingListDeleteDialog} />
    </>
  );
}


export default Routes;
