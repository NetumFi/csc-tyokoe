import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './search-setting.reducer';

export const SearchSettingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const searchSettingEntity = useAppSelector(state => state.searchSetting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="searchSettingDetailsHeading">
          <Translate contentKey="csc2022App.searchSetting.detail.title">SearchSetting</Translate>
        </h2>
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
            <span id="educationLevel">
              <Translate contentKey="csc2022App.searchSetting.educationLevel">Education Level</Translate>
            </span>
          </dt>
          <dd>{searchSettingEntity.educationLevel}</dd>
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
        </dl>
        <Button tag={Link} to="/search-setting" replace color="info" data-cy="entityDetailsBackButton">
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
