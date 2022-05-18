import { IUser } from 'app/shared/model/user.model';

export interface ISearchSetting {
  id?: number;
  searchTerm?: string | null;
  educationLevel?: string | null;
  role?: string | null;
  age?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ISearchSetting> = {};
