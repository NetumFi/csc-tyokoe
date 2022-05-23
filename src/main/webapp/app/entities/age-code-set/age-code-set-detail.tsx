import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './age-code-set.reducer';

export const AgeCodeSetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const ageCodeSetEntity = useAppSelector(state => state.ageCodeSet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ageCodeSetDetailsHeading">
          <Translate contentKey="csc2022App.ageCodeSet.detail.title">AgeCodeSet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ageCodeSetEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="csc2022App.ageCodeSet.code">Code</Translate>
            </span>
          </dt>
          <dd>{ageCodeSetEntity.code}</dd>
          <dt>
            <span id="codeId">
              <Translate contentKey="csc2022App.ageCodeSet.codeId">Code Id</Translate>
            </span>
          </dt>
          <dd>{ageCodeSetEntity.codeId}</dd>
          <dt>
            <span id="labelEn">
              <Translate contentKey="csc2022App.ageCodeSet.labelEn">Label En</Translate>
            </span>
          </dt>
          <dd>{ageCodeSetEntity.labelEn}</dd>
          <dt>
            <span id="labelFi">
              <Translate contentKey="csc2022App.ageCodeSet.labelFi">Label Fi</Translate>
            </span>
          </dt>
          <dd>{ageCodeSetEntity.labelFi}</dd>
          <dt>
            <span id="labelSv">
              <Translate contentKey="csc2022App.ageCodeSet.labelSv">Label Sv</Translate>
            </span>
          </dt>
          <dd>{ageCodeSetEntity.labelSv}</dd>
        </dl>
        <Button tag={Link} to="/age-code-set" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/age-code-set/${ageCodeSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgeCodeSetDetail;
