import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Age from './age';
import AgeDetail from './age-detail';
import AgeUpdate from './age-update';
import AgeDeleteDialog from './age-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AgeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AgeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AgeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Age} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AgeDeleteDialog} />
  </>
);

export default Routes;
