import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SearchHistory from './search-history';
import SearchHistoryDetail from './search-history-detail';
import SearchHistoryUpdate from './search-history-update';
import SearchHistoryDeleteDialog from './search-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SearchHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SearchHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SearchHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={SearchHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SearchHistoryDeleteDialog} />
  </>
);

export default Routes;
