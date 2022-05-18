import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './age.reducer';

export const AgeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ageEntity = useAppSelector(state => state.age.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ageDetailsHeading">
          <Translate contentKey="csc2022App.age.detail.title">Age</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ageEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="csc2022App.age.code">Code</Translate>
            </span>
          </dt>
          <dd>{ageEntity.code}</dd>
          <dt>
            <span id="codeId">
              <Translate contentKey="csc2022App.age.codeId">Code Id</Translate>
            </span>
          </dt>
          <dd>{ageEntity.codeId}</dd>
          <dt>
            <span id="labelEn">
              <Translate contentKey="csc2022App.age.labelEn">Label En</Translate>
            </span>
          </dt>
          <dd>{ageEntity.labelEn}</dd>
          <dt>
            <span id="labelFi">
              <Translate contentKey="csc2022App.age.labelFi">Label Fi</Translate>
            </span>
          </dt>
          <dd>{ageEntity.labelFi}</dd>
          <dt>
            <span id="labelSv">
              <Translate contentKey="csc2022App.age.labelSv">Label Sv</Translate>
            </span>
          </dt>
          <dd>{ageEntity.labelSv}</dd>
        </dl>
        <Button tag={Link} to="/age" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/age/${ageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgeDetail;
