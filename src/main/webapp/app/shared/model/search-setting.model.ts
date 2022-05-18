import { IUser } from 'app/shared/model/user.model';
import { IEducationLevelCodeSet } from 'app/shared/model/education-level-code-set.model';
import { IAgeCodeSet } from 'app/shared/model/age-code-set.model';

export interface ISearchSetting {
  id?: number;
  searchTerm?: string | null;
  role?: string | null;
  age?: string | null;
  user?: IUser | null;
  educationLevelCodeSet?: IEducationLevelCodeSet | null;
  ageCodeSet?: IAgeCodeSet | null;
}

export const defaultValue: Readonly<ISearchSetting> = {};
