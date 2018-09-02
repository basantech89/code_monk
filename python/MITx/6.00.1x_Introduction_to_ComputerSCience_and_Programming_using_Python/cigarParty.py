def cigar_party(cigars, is_weekend):
    if 40 <= cigars <= 60 and not is_weekend:
        return True
    elif 40 <= cigars and is_weekend:
        return True
    else:
        return False
print cigar_party(30, False)                                   
print cigar_party(50, False)
print cigar_party(70, True)
print cigar_party(30, True)