import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('SearchSetting e2e test', () => {
  const searchSettingPageUrl = '/search-setting';
  const searchSettingPageUrlPattern = new RegExp('/search-setting(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const searchSettingSample = {};

  let searchSetting: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/search-settings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/search-settings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/search-settings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (searchSetting) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/search-settings/${searchSetting.id}`,
      }).then(() => {
        searchSetting = undefined;
      });
    }
  });

  it('SearchSettings menu should load SearchSettings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('search-setting');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SearchSetting').should('exist');
    cy.url().should('match', searchSettingPageUrlPattern);
  });

  describe('SearchSetting page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(searchSettingPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SearchSetting page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/search-setting/new$'));
        cy.getEntityCreateUpdateHeading('SearchSetting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchSettingPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/search-settings',
          body: searchSettingSample,
        }).then(({ body }) => {
          searchSetting = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/search-settings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/search-settings?page=0&size=20>; rel="last",<http://localhost/api/search-settings?page=0&size=20>; rel="first"',
              },
              body: [searchSetting],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(searchSettingPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SearchSetting page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('searchSetting');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchSettingPageUrlPattern);
      });

      it('edit button click should load edit SearchSetting page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SearchSetting');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchSettingPageUrlPattern);
      });

      it('last delete button click should delete instance of SearchSetting', () => {
        cy.intercept('GET', '/api/search-settings/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('searchSetting').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', searchSettingPageUrlPattern);

        searchSetting = undefined;
      });
    });
  });

  describe('new SearchSetting page', () => {
    beforeEach(() => {
      cy.visit(`${searchSettingPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SearchSetting');
    });

    it('should create an instance of SearchSetting', () => {
      cy.get(`[data-cy="searchTerm"]`).type('Pizza').should('have.value', 'Pizza');

      cy.get(`[data-cy="educationLevel"]`).type('Romania Marketing').should('have.value', 'Romania Marketing');

      cy.get(`[data-cy="role"]`).type('navigate').should('have.value', 'navigate');

      cy.get(`[data-cy="age"]`).type('Mouse clicks-and-mortar services').should('have.value', 'Mouse clicks-and-mortar services');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        searchSetting = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', searchSettingPageUrlPattern);
    });
  });
});
