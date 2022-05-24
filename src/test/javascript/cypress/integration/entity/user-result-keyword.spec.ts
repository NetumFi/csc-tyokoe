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

describe('UserResultKeyword e2e test', () => {
  const userResultKeywordPageUrl = '/user-result-keyword';
  const userResultKeywordPageUrlPattern = new RegExp('/user-result-keyword(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userResultKeywordSample = {};

  let userResultKeyword: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-result-keywords+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-result-keywords').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-result-keywords/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userResultKeyword) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-result-keywords/${userResultKeyword.id}`,
      }).then(() => {
        userResultKeyword = undefined;
      });
    }
  });

  it('UserResultKeywords menu should load UserResultKeywords page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-result-keyword');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserResultKeyword').should('exist');
    cy.url().should('match', userResultKeywordPageUrlPattern);
  });

  describe('UserResultKeyword page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userResultKeywordPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserResultKeyword page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-result-keyword/new$'));
        cy.getEntityCreateUpdateHeading('UserResultKeyword');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userResultKeywordPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-result-keywords',
          body: userResultKeywordSample,
        }).then(({ body }) => {
          userResultKeyword = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-result-keywords+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/user-result-keywords?page=0&size=20>; rel="last",<http://localhost/api/user-result-keywords?page=0&size=20>; rel="first"',
              },
              body: [userResultKeyword],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userResultKeywordPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserResultKeyword page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userResultKeyword');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userResultKeywordPageUrlPattern);
      });

      it('edit button click should load edit UserResultKeyword page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserResultKeyword');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userResultKeywordPageUrlPattern);
      });

      it('last delete button click should delete instance of UserResultKeyword', () => {
        cy.intercept('GET', '/api/user-result-keywords/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('userResultKeyword').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', userResultKeywordPageUrlPattern);

        userResultKeyword = undefined;
      });
    });
  });

  describe('new UserResultKeyword page', () => {
    beforeEach(() => {
      cy.visit(`${userResultKeywordPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserResultKeyword');
    });

    it('should create an instance of UserResultKeyword', () => {
      cy.get(`[data-cy="resultKeyword"]`).type('Cambridgeshire').should('have.value', 'Cambridgeshire');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        userResultKeyword = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', userResultKeywordPageUrlPattern);
    });
  });
});
