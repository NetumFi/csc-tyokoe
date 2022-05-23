import './search-material.scss';

import React, {useEffect} from 'react';
import {translate, Translate, ValidatedForm, ValidatedField} from 'react-jhipster';
import {
  Row,
  Col,
  Button,
  Input,
  Badge,
  Container, Table, Card, CardImg, CardBody, CardTitle, CardFooter, CardText
} from 'reactstrap';

import {useAppDispatch, useAppSelector} from 'app/config/store';
import {handleSearch, addFilter, deleteFilter, setSearchTerms} from "app/modules/search-material/search-material.reducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {getEntities} from "app/entities/education-level-code-set/education-level-code-set.reducer";
import {Link} from "react-router-dom";


export const SearchMaterial = (props) => {
  const account = useAppSelector(state => state.authentication.account);
  const searchparams = useAppSelector(state => state.searchMaterial);
  const educationLevelCodeSets = useAppSelector(state => state.educationLevelCodeSet);
  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntities({page: 0, size: 100, sort: null}));
  }, [props.location]);

  const filterItems = educationLevelCodeSets.entities;

  const handleValidSubmit = (data) => {
    // eslint-disable-next-line no-console
    console.log("##### 11111", data);
    dispatch(handleSearch(data));
  };

  const handleAddFilter = (params) => {
    const rajaus = filterItems.filter(i => i.codeId === params.target.value)[0];
    dispatch(addFilter(rajaus));
  };

  const handleDeleteFilter = (filter) => {
    dispatch(deleteFilter(filter));
  }

  const setInputSearchTerms = (value) => {
    dispatch(setSearchTerms(value.target.value));
  }

  const codeLocales = {"fi": "labelFi", "en": "labelEn", "sv": "labelSv"};

  const sort = ["Suosituin ensin"];

  return (
    <Container>
        <h2>
          <Translate contentKey="materialsearch.title.welcome" interpolate={{name: account?.firstName}}>
            Tervetuloa, {account?.firstName}.
          </Translate>
        </h2>
        <Container>
        <ValidatedForm id="materialsearch-form" onSubmit={handleValidSubmit} defaultValues={searchparams}>
          <Row>
            <Col sm="10">
              <Input
                type="text"
                name="searchTerms"
                label={translate('materialsearch.form.searchterms')}
                placeholder={translate('materialsearch.form.searchterms-placeholder')}
                data-cy="searchTerms"
                onChange={setInputSearchTerms}
              />
            </Col>
            <Col xs="1">
              <Button color="primary" type="submit" data-cy="submit">
                <Translate contentKey="materialsearch.form.button">Search</Translate>
              </Button>
            </Col>
          </Row>
          <Row>
            <Col
              lg="3">
              <Input
                name="filter"
                label={translate('materialsearch.form.filter')}
                id="filter"
                placeholder={translate('materialsearch.form.filter')}
                type="select"
                data-cy="filter"
                onChange={handleAddFilter}
                defaultValue={null}>
                {
                  [<option key="firstElement"> {translate('materialsearch.form.filter')} </option>]
                    .concat(filterItems.filter(item => !searchparams.filters.includes(item)).map(filterItem => (
                      <option value={filterItem.codeId} key={filterItem.codeId}>
                        {filterItem[codeLocales[currentLocale]]}
                      </option>
                    )))
                }
              </Input>
            </Col>
            <Col sm="9" className="filterList">
              {searchparams.filters.map(filterItem =>
                <Badge
                  key={filterItem.codeId}
                  className="filterChip"
                  tabIndex={0}
                  color="primary"
                  onClick={() => handleDeleteFilter(filterItem)}
                  pill>
                  {filterItem["labelFi"]}
                  <span className="icon">
                    <FontAwesomeIcon icon="x"/>
                  </span>
                </Badge>)}
            </Col>
          </Row>
          <Row>
            <Col sm="3">
              <Input
                type="select">
                {sort.map(s => <option key={s}>{s}</option>)}
              </Input>
            </Col>
          </Row>
        </ValidatedForm>
        </Container>

      {searchparams.material?.hits && <p>Hakutuloksia {searchparams.material?.hits}</p>}
      <div className="results">
        {searchparams.material?.results.map(result => {
          return (
            <div key={result.key} className="card">
              <div className="row no-gutters">
                <div className="col-auto">
                  {result.thumbnail?.filepath &&
                    <img src={result.thumbnail?.filepath} className="img-fluid" alt="img"/>}
                </div>
                <div className="col">
                  <div className="card-block px-2">
                    <h4 className="card-title">
                      {result.materialName.filter(m => m.language === currentLocale).map(m => m.materialname)[0]}
                    </h4>
                    <p className="card-text">
                      {result.description.filter(d => d.language === currentLocale).map(d => d.description)[0]}
                    </p>
                    <div>
                      {result.learningResourceTypes.map(t =>
                        <Badge
                          key={t.learningresourcetypekey}
                          color="primary"
                          pill>
                          {t.value}
                        </Badge>)}
                    </div>
                  </div>
                </div>
              </div>
              <div className="card-footer w-100 text-muted">
                {result.updatedAt}
              </div>
            </div>)
        })}
      </div>
    </Container>
  );
};

export default SearchMaterial;
