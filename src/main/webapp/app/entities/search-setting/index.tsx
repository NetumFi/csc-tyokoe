import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SearchSetting from './search-setting';
import SearchSettingDetail from './search-setting-detail';
import SearchSettingUpdate from './search-setting-update';
import SearchSettingDeleteDialog from './search-setting-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SearchSettingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SearchSettingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SearchSettingDetail} />
      <ErrorBoundaryRoute path={match.url} component={SearchSetting} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SearchSettingDeleteDialog} />
  </>
);

export default Routes;
