import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, AUTHORITIES} from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './search-setting.reducer';
import {hasAnyAuthority} from "app/shared/auth/private-route";

export const SearchSettingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const searchSettingEntity = useAppSelector(state => state.searchSetting.entity);
  return (
    <Row>
      <Col md="8">
        <h1 className="h2" data-cy="searchSettingDetailsHeading">
          <Translate contentKey="csc2022App.searchSetting.detail.title">SearchSetting</Translate>
        </h1>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{searchSettingEntity.id}</dd>
          <dt>
            <span id="searchTerm">
              <Translate contentKey="csc2022App.searchSetting.searchTerm">Search Term</Translate>
            </span>
          </dt>
          <dd>{searchSettingEntity.searchTerm}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="csc2022App.searchSetting.role">Role</Translate>
            </span>
          </dt>
          <dd>{searchSettingEntity.role}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="csc2022App.searchSetting.age">Age</Translate>
            </span>
          </dt>
          <dd>{searchSettingEntity.age}</dd>
          <dt>
            <Translate contentKey="csc2022App.searchSetting.user">User</Translate>
          </dt>
          <dd>{searchSettingEntity.user ? searchSettingEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="csc2022App.searchSetting.educationLevelCodeSet">Education Level Code Set</Translate>
          </dt>
          <dd>{searchSettingEntity.educationLevelCodeSet ? searchSettingEntity.educationLevelCodeSet.id : ''}</dd>
          <dt>
            <Translate contentKey="csc2022App.searchSetting.ageCodeSet">Age Code Set</Translate>
          </dt>
          <dd>{searchSettingEntity.ageCodeSet ? searchSettingEntity.ageCodeSet.id : ''}</dd>
        </dl>
        <Button tag={Link} to= {isAdmin ? "/search-setting" : "/user-search-settings" }  replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/search-setting/${searchSettingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SearchSettingDetail;
