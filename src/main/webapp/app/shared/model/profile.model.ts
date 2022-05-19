import { IUser } from 'app/shared/model/user.model';

export interface IProfile {
  id?: number;
  keywords?: string | null;
  education?: string | null;
  language?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IProfile> = {};
