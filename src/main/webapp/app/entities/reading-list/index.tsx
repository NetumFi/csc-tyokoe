import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ReadingList from './reading-list';
import ReadingListDetail from './reading-list-detail';
import ReadingListUpdate from './reading-list-update';
import ReadingListDeleteDialog from './reading-list-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReadingListUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReadingListUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReadingListDetail} />
      <ErrorBoundaryRoute path={match.url} component={ReadingList} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReadingListDeleteDialog} />
  </>
);

export default Routes;
